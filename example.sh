echo -e "Creating Account 1 with RUB currency: "
curl -f -X PUT 'http://localhost:8080/api/account/1?currency=RUB' || exit 1

echo -e "\n\nCreating Account 2 with RUB currency: "
curl -f -X PUT 'http://localhost:8080/api/account/2?currency=RUB'  || exit 1

echo -e "\n\nAdding 100 RUB to Account 1: "
curl -f -f -X PUT 'http://localhost:8080/api/account/1/100/RUB' || exit 1

echo -e "\n\nTransferring 20 RUB from Account 1 to Account 2: "
curl -f -X POST 'http://localhost:8080/api/account/1/transfer/2?amount=20&currency=RUB' || exit 1

echo -e "\n\nAccount 1 balance 80: "
curl -f -X GET 'http://localhost:8080/api/account/1' || exit 1

echo -e "\n\nAccount 2 balance 20: "
curl -f -X GET 'http://localhost:8080/api/account/2' || exit 1

echo -e "\n\nOK"
