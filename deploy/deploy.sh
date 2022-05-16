cd ../hy-notice
./gradlew :hy-notice-client:bootJar
./gradlew :hy-notice-api:bootJar
cp ./hy-notice-client/build/libs/hy-notice-client-1.0-SNAPSHOT.jar ../deploy/hy-notice-client
cp ./hy-notice-api/build/libs/hy-notice-api-1.0-SNAPSHOT.jar ../deploy/hy-notice-api

cd ../deploy
docker build -t hy-notice-client:latest ./hy-notice-client
docker tag hy-notice-client:latest junroot0909/hy-notice-client:latest
docker push junroot0909/hy-notice-client:latest

docker build -t hy-notice-api:latest ./hy-notice-api
docker tag hy-notice-api:latest junroot0909/hy-notice-api:latest
docker push junroot0909/hy-notice-api:latest
