#script d'execution Du pendu
rm -rvf ./SourceCode/bin
javac --module-path ./dependencies/JavaFX/ --add-modules javafx.controls,javafx.fxml -d ./SourceCode/bin ./SourceCode/src/*.java
java --module-path ./dependencies/JavaFX/ --add-modules javafx.controls,javafx.fxml -cp ./SourceCode/bin Pendu