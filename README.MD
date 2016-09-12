###REST WEB API

Run the app

```bash
mvn jetty:run
```

And run the test
```bash
bash example.sh
```

or use the following commands:

* Creating an account
```bash
	curl -fv -X PUT 'http://localhost:8080/api/account/1?currency=RUB'
	curl -fv -X PUT 'http://localhost:8080/api/account/2?currency=RUB'
```
* Retrieving account 1
```bash
curl -fv -X GET 'http://localhost:8080/api/account/1'
```
* Making a deposit to account 1
```bash
curl -fv -X PUT 'http://localhost:8080/api/account/1/100/RUB'
```
* Making a withdrawal from account 1
```bash
curl -fv -X DELETE 'http://localhost:8080/api/account/1/50/RUB'
```
* Wire transfer from account 1 to account 2
```bash
curl -fv -X POST 'http://localhost:8080/api/account/1/transfer/2?amount=20&currency=RUB'
```
