LANG=en_US.UTF-8
./sbt dist
cd ./target/universal/
unzip ./fineplay-2.6.13-β7-SNAPSHOT.zip
cd fineplay-2.6.13-β7-SNAPSHOT
./bin/fineplay -Dconfig.resource=application_prod.conf -Dlogger.resource=logback_prod.xml
