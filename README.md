# Microservice

Spring Boot + Microservice + Swagger2

# Necessario

- JDK 11
- MySQL
- Maven

# Docs

- http://localhost:8080/gateway/order/swagger-ui.html#
- http://localhost:8080/gateway/product/swagger-ui.html#
- http://localhost:8080/gateway/user/swagger-ui.html#

# Passo a passo - Setup

- Clone o projeto
- Baixe as dependencias do pom da raiz

Cria a base "desafio" no seu MySQL e configure a conexão no properties:

![alt text](https://artcousan.github.io/img/Datasource%20config.PNG)

Ordem de execução dos applications:

![alt text](https://artcousan.github.io/img/Ordem%20de%20execução.PNG)

Discovery:

![alt text](https://artcousan.github.io/img/Discovery.PNG)

![alt text](https://artcousan.github.io/img/Instancias%20registradas.png)

# Fluxo Feliz

## 1 - Se cadastre no sistema:

![alt text](https://artcousan.github.io/img/1%20-%20Cadastre-se%20no%20sistema.PNG)

## 2 - Realize login:

![alt text](https://artcousan.github.io/img/2%20-%20Realize%20login.PNG)

## 3 - Verifique o header da requisição e copie do token, pois será utilizado em todo sistema:

![alt text](https://artcousan.github.io/img/3%20-%20Pegue%20o%20Bearer%20Token%20no%20Header.PNG)

## 4 - Cadastre um produto

![alt text](https://artcousan.github.io/img/4%20-%20Cadastre%20um%20produto.PNG)

## 5 - Veja o produto cadastrado no body

![alt text](https://artcousan.github.io/img/5%20-%20Veja%20no%20body%20o%20produto.PNG)

## 6 - Ou liste todos produtos

![alt text](https://artcousan.github.io/img/6%20-%20Ou%20list%20os%20produtos.PNG)

## 7 - Escolha um usuario para criar o pedido

![alt text](https://artcousan.github.io/img/7%20-%20List%20os%20usuarios%20e%20escolha%20um.PNG)

## 8 - Realize um pedido

![alt text](https://artcousan.github.io/img/8%20-%20Realize%20um%20pedido.PNG)

## 9 - Pode-se se notar que a quantidade presente do produto diminuiu

![alt text](https://artcousan.github.io/img/9%20-%20Removido%20a%20quantidade.PNG)

## 10 - E o pedido cadastrado para o usuario

![alt text](https://artcousan.github.io/img/10%20-%20Pedido.PNG)


# Autor

```
Arthur Coutinho Santos - RM 336256
```
