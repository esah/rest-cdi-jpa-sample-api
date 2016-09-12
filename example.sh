curl -v -X PUT 'http://localhost:8080/api/account/1'
curl -v -X PUT 'http://localhost:8080/api/account/2'
curl -v -X PUT 'http://localhost:8080/api/account/3?currency=USD'

curl -v -X PUT 'http://localhost:8080/api/account/1/100.2/RUB'
curl -v -X PUT 'http://localhost:8080/api/account/1/500/RUB'
curl -v -X DELETE 'http://localhost:8080/api/account/1/50/RUB'

curl -v -X GET  'http://localhost:8080/api/account/1'

curl -v -X POST 'http://localhost:8080/api/account/1/transfer/2?amount=10.50&currency=RUB'
