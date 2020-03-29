@ECHO OFF
set QUERY_PORT=8095

set RABBIT_CONCURRENCY=1
set RABBIT_ADDRESSES=127.0.0.1:5672
set RABBIT_USER=guest
set RABBIT_PASSWORD=guest
set RABBIT_QUEUE=zipkin
set RABBIT_VIRTUAL_HOST=/

set STORAGE_TYPE=mysql
set MYSQL_HOST=127.0.0.1
set MYSQL_TCP_PORT=3306
set MYSQL_USER=root
set MYSQL_PASS=
set MYSQL_DB=zipkin
set MYSQL_USE_SSL=false

set HTTP_COLLECTOR_ENABLED=false
set COLLECTOR_SAMPLE_RATE=1.0

::windows 10 64bit�汾��Ĭ�ϲ�������zipkin server �ᱨ��Native memory allocation (malloc) failed to allocate 360816 bytes for Chunk::new
::��Ҫ����ReservedCodeCacheSize��С
::linux shell��δ����
set JAVA_OPTS="-XX:ReservedCodeCacheSize=64m" 
java -jar zipkin-server-2.11.8-exec.jar
