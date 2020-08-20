# CryptoWebService

CrytoWebService supports three API

1.PUSHANDRECALCULATE API
pushAndRecalculate(num) -> Push data to running stream and return mean and standard deviation

Example CURL Command:

curl --location --request POST 'http://localhost:8100/crypto/api/v1/pushAndRecalculate' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'number=4'

2.PUSHRECALCULATEANDENCRYPT API
pushRecalculateAndEncrypt(num) -> Push data to running stream and return encrypted mean and standard deviation

Example CURL Command:

curl --location --request POST 'http://localhost:8100/crypto/api/v1/pushRecalculateAndEncrypt' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'number=9'

3. DECRYPT API
decrypt(encrypted_string) -> Returns the decrypted string

Example CURL Command:

curl --location --request GET 'http://localhost:8100/crypto/api/v1/decrypt/wExwmFDac7a350hz2j5gDjSokcRp_PQf0xLQ5RtbI30'

Maven commands

cd project_directory

mvn clean package

cd target

java -jar crypto-webservices-1.0-SNAPSHOT.jar


