###REST WEB API

Run the app

```bash
mvn clean jetty:run
```

Use http://localhost:8080

1 Creating an account
	PUT /account?currency=rub

2 Retrieving account 1
	GET /account/1

3 Making a deposit to account 1
	PUT /account/1/200/rub

4 Making a withdrawal from account 1
  	DELETE /account/1/200/rub

5 Wire transfer from account 1 to account 2
	POST /account/1/transfer/2?amount=200&currency=rub
