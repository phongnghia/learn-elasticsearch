# LEARN ELASTICSEARCH

### Update system variable
``` code
sudo vi /etc/sysctl.conf

# vm.max_map_count=262144

sudo sysctl -p
```

### Start elasticsearch

``` code
docker network create elasticsearch

docker run -d --name elasticsearch --net elastic -p 9200:9200 \
           -it -m 6GB -e "xpack.ml.use_auto_machine_memory_percent=true" \
           -e "xpack.security.enabled=false" elasticsearch:8.17.1
```
### Get password from container logs

``` code
docker logs $(docker ps -a | grep elasticsearch | awk '{print $1}')
```

### Reset elastic password

``` code
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
```

### Create enrollment token to kibana

``` code
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana
```

### Copy cacert

```code
docker cp elasticsearch:/usr/share/elasticsearch/config/certs/http_ca.crt .
```

### Start Kibana

```code
docker run -d --name kibana --net elastic \
           -p 5601:5601 kibana:8.17.1
```

### Update cacert when running spring boot application

```code
Run with root user

echo %JAVA_HOME%

%JAVA_HOME%\lib\security\cacerts

keytool -import -trustcacerts -keystore "%JAVA_HOME%\lib\security\cacerts" ^
        -storepass changeit -noprompt -alias elasticsearch -file http_ca.crt


Remove alias (Run as Administration)

keytool -delete -alias elasticsearch -keystore "%JAVA_HOME%\lib\security\cacerts"

```

### Run spring boot application

```code

mvn clean spring-boot:run -Dpassword='<ES_PASSWORD>'

```