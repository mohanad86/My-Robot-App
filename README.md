# SumoRobot controls over the Android application 

![alt text](https://github.com/mohanad86/My-Robot-App/blob/master/images/Screenshot_2016-08-10-21-59-07.png)
![alt text](https://github.com/mohanad86/My-Robot-App/blob/master/images/Screenshot_2016-08-10-21-52-44.png)
![alt text](https://github.com/mohanad86/My-Robot-App/blob/master/images/20160810_220841.jpg)
![alt text](https://github.com/mohanad86/webrobo/blob/master/images/Screenshot%20from%202016-05-03%2017-39-09.jpg)


### If you are using Ubuntu platform
 
- Install Java Android application studio.(https://developer.android.com/studio/install.html)
- Check for the java version it should be above 1.7
- Install git into you Machine to pull and push from the server.
- Update Java into your computer

The following commands

```sh
$ java -version
After you check for the Java version and you found it lower than 1.8
Try to install it using the following commands
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
This is the easiest way to install the new version of Java
```

```sh
Don't forget to install the following libraries to make the program work 
if you are running a 64-bit version of Ubuntu, you need to install some 
32-bit libraries with the following command
$ sudo apt-get install lib32z1 lib32ncurses5 lib32bz2-1.0 lib32stdc++6
```

```sh
Extra for using Github and the text editor
$ sudo apt-get install git
$ sudo apt-get install gedit
```

#Follow this instructions to clone to my Repo
- Clone to the my repository from terminal
```sh 
$ git clone https://github.com/mohanad86/My-Robot-App
$ git pull 
$ cd My-Robot-App
``` 
Then you are in :)



    Author: Mohanad Aly 

License
----
Licensed under the Apache License

http://www.apache.org/licenses/LICENSE-2.0
