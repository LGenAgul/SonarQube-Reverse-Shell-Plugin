# Description
This project demonstrates a Java-based SonarQube plugin designed to execute a reverse shell back to a listener (like Netcat) upon the initialization of the SonarQube server or during a project analysis.
It correctly identifies the operating system and issues a shell based on that
## How it works
The plugin implements the org.sonar.api.Plugin interface. When SonarQube starts, it calls the define() method of all installed plugins. This payload hijacks that initialization phase to spawn a background process.
## Variables to change inside the code:
Inside the code your listener ip address and ports need to be changed
