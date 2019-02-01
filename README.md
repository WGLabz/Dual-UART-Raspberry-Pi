Started working on project on 20190128 Dual UART Raspberry Pi 

### Libraries Used:

* [Pi4J](https://github.com/Pi4J/pi4j/)

### Enable hardware UART of Raspberry Pi
* goto `raspi-config` and change use hardware serial to yes in interface menu.
* type in `sudo /boot/config.txt` and add `dtoverlay=pi3-disable-bt`
* Run `sudo systemctl disable hciuart`