# Thunderbird Senior Project
Senior Project for Software Engineering BYU-I Spring 2023

# Overview
Thunderbird is an app that will be available on Android devices. It will take in information about the size and weight of pallets that are to be transported in a container. The app will take this information and allow the user to virtually pack the cargo into the container with drag and drop. This will help the user to quickly and safely load containers to help make the process of loading more efficient by moving the trial-and-error to the handheld instead of the container.

The objective of this application is to enable truckers and dock workers to efficiently and effectively load containers given a set of freight and the containers’ capacities. It is an Android application written in Kotlin. It will store information about the cargo and containers locally and on a network using Google Cloud Firestore, providing offline service for workers when away from cell service while still allowing for updates via wireless internet when at the docks.