#script d'execution Du pendu
rm -rvf ./bin/
javac -d ./bin/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml ./src/*.java
java -cp ./bin/ Pendu