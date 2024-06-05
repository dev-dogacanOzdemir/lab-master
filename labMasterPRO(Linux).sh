#!/bin/bash
set -e

# Java home ve PATH ayarları
export JAVA_HOME="/usr/lib/jvm/java-21-openjdk"
export PATH="$JAVA_HOME/bin:$PATH"

# Veritabanını oluştur ve doldur
bash bin/create_db(LINUX).sh

# Uygulamayı çalıştır
java -jar bin/lab-master-v1.0-0.0.1-SNAPSHOT.jar
