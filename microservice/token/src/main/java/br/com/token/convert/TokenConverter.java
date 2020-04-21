package br.com.token.convert;

import br.com.core.property.JWTConfiguration;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;

@Service
public class TokenConverter {

    private final JWTConfiguration jwtConfiguration;

    public TokenConverter(JWTConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String decryptTOken(String encryptedToken) {
        JWEObject jweObject = null;
        DirectDecrypter directDecrypter = null;
        try {
            jweObject = JWEObject.parse(encryptedToken);
            directDecrypter = new DirectDecrypter((jwtConfiguration.getPrivateKey().getBytes()));
            jweObject.decrypt(directDecrypter);
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jweObject.getPayload().toSignedJWT().serialize();
    }

    public void validateTokenSignature(String signedToken) throws ParseException, JOSEException, AccessDeniedException {
        SignedJWT signedJWT = SignedJWT.parse(signedToken);
        RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
        if (!signedJWT.verify(new RSASSAVerifier(publicKey)))
            throw new AccessDeniedException("Invalid token");
    }

}
