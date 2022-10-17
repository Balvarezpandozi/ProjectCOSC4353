__COSC 4353 - Software Design__
> Prof. Amin Alipour 

#### Authors:
- Bryan Alvarez
- Atharva Rajurkar
- Diogo Aires

# SEMESTER PROJECT

### Description:
The goal of the project in this course is to design and implement a software system with the principles that you learn during the course. The project is a group assignment. Each group is composed of 2 or 3 students that are randomly assigned by the instruction staff.

### Here are the details:
You will extract the core functional and non-functional requirements, and prototype appropriate command-line java solutions. You will be using agile methodology: 2-week sprints/ biweekly group and personal reports for __Wireshark (https://www.wireshark.org/)__

### Member sprint instructions
1. For whatever task you take on, create new branch 
2. Update the task backlog and work on the task 
3. If you need help, ask for it so that the others can jump in and help you out 
4. When done submit pull request 
5. Request other members review 
6. Other members review your branch 
7. Approve it, or request changes 
8. Fix changes 
9. Approve pull request 
10. Merge branch to the master 
11. Test master with new branch

### Sprints:
#### Sprint 1: Sep 21, 2022
- Created repository
- Added ReadMe file
- Created tag
#### Sprint 2: Sep 21, 2022 - Sep 28, 2022
- Task1: Add packet capturing library
- Task2: Create command line class to handle user input
- Task3: Add basic packet capture functionality
- Task4: Integrate different components on main class
- Task5: Add junit dependency in pom.xml
- Task6: ipv4 or ipv6 address checker
#### Sprint 3: Sep 28, 2022 - Oct 5, 2022
- Task7: Writing packets into a file
#### Sprint 4: Oct 5, 2022 - Oct 19, 2022
- Task2: Create command line class to handle user input
- Task4: Integrate different components on main class
- Task8: Reading packets from pcap file
- Task9: Add reading packets given an amount of time on an interface
- Task10: Add junit tests
- Task11: Set up general test class to implement samples and calling all tests in one class
#### Sprint 5: Oct 19, 2022 - Nov 2, 2022
#### Sprint 6: Nov 2, 2022 - Nov 16, 2022
#### Project Presentation: Nov 28, 2022


### Task Backlog
#### Tasks ToDo
- Task4: Integrate different components on main class

#### WIP (Task name, team member(s) working on it)
- Task9: Add reading packets given an amount of time on an interface, Atharva
- Task10: Add junit tests, Atharva
- Task2: Create command line class to handle user input, Diogo
#### Tasks Done
- Created repository
- Added ReadMe file
- Created tag
- Task5: Add junit dependency in pom.xml, Atharva
- Task1: Add packet capturing library, Bryan
- Task3: Add basic packet capture functionality, Bryan
- Task6: ipv4 or ipv6 address checker, Diogo
- Task7: Writing packets into a file, Bryan
- Task8: Reading packets from pcap file, Bryan
- Task11: Set up general test class to implement samples and calling all tests in one class, Bryan

### Running Instructions:

### File Structure:
> If extension is present, it is a file. Otherwise, folder

- .idea: this folder stores custom run/debug configurations, git repository mapping and other project specific info.
  - .gitignore(file): to specify files that will not be uploaded to the repository
- src: All code we write.
  - main: Application code
  - test: All tests code
- target: Holds all output of the build.

### Technical Details:

## Main Functionalities of Wireshark to Analyze for Project 
- Packet capture -> wireshark listens to a network connection in real time and grabs entire streams of traffic (can be up to thousands of packets)
- Packet monitor -> displays source and destination addresses, the protocol used, contents of packet in text/encoded format and if applicable destination port.
- Filtering -> Slices and dices, the sniffed random data using filters to obtain specific data in needed by the user
  - Filtering can be based on:
    - ip address
    - port
    - protocol
    - keyword in data packets
- Visualization -> wireshark allows user to analyze into the very middle of a network packet, entire conversations (messages between server and client), and network streams.
- Output export -> The packet info can be exported and saved in different file formats like xml, csv or plain text.
