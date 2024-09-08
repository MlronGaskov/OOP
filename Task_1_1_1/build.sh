#!/bin/bashc

javac -d src/main/java src/main/java/ru/nsu/gaskov/HeapSort.java

# Создаем JAR файл
jar cf src/main/java/ru/nsu/gaskov/HeapSort.jar -C src/main/java ru/nsu/gaskov/HeapSort.class

# Генерируем документацию
javadoc -d docs src/main/java/ru/nsu/gaskov/HeapSort.java

# Запускаем программу
java -cp src/main/java ru.nsu.gaskov.HeapSort