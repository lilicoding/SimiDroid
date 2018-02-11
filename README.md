# SimiDroid

Identifying and Explaining Similarities in Android Apps

For more information, please check the following paper:

```
@inproceedings{li2017simidroid,
title={SimiDroid: Identifying and Explaining Similarities in Android Apps},
author={Li, Li and Bissyand{\'e}, Tegawend{\'e} F and Klein, Jacques},
booktitle={The 16th IEEE International Conference On Trust, Security And Privacy In Computing And Communications (TrustCom 2017)},
year={2017}
}
```

# Setup

The simidroid_config.xml configuration file provides an interface for users of SimiDroid to specify what kinds of similarities (e.g., resource-level or code-level) they want to apply.
In particular, there are two things that users need to keep in mind in order to customise simidroid_config.xml.

* AndroidJarsPath: Using this element to specify the path of android-platforms (cf. https://github.com/lilicoding/android-platforms).
* Plugin: Using this element to specify the needed plugins. So far, SimiDroid has implemented three types of plugins: METHOD (i.e., method-level), COMPONENT (i.e., component-level), and RESOURCE (i.e., resource-level).

The following script demonstrates an example of configured simidroid_config.xml.
Based on this configuration, components between two Android apps will be compared.

```
<SimiDroid>
	<AndroidJarsPath>~/github/android-platforms</AndroidJarsPath>
	<Plugin name="COMPONENT" />
</SimiDroid>
```

# Execution

* For two apps

For comparing the similarity of two Android apps, the parameters of SimiDroid should be the exact paths of these two apps.

```
java -jar SimiDroid.jar ~/apps/WhatsAppMessenger.apk ~/apps/Snapchat.apk

```

* For multiple apps

For comparing the similarity of multiple Android apps, the parameters of SimiDroid can be (1) the exact paths of to-be-analyzed apps or (2) the directory path of the to-be-analyzed apps.

```
java -jar SimiDroid.jar ~/apps/WhatsAppMessenger.apk ~/apps/Snapchat.apk ~/apps/Facebook.apk
java -jar SimiDroid.jar ~/apps/

```

