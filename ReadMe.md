LinkCal
Overview
LinkCal is a mobile application designed to simplify social scheduling. At its core, it allows users to connect with friends, family, or colleagues by "linking" calendars. Instead of sending endless texts to find a time to meet, LinkCal lets users share their availability and view connected calendars to easily coordinate plans while maintaining control over their privacy.

Core Features
1. User Accounts & Security

Secure Onboarding: Users can create an account using their email and name.
Authentication: Secure login system ensuring that personal schedule data remains private to the account holder.
Session Management: The app remembers the user's login state for quick access upon returning.
2. Social Calendar Linking

Unique Identification: The app operates on a User ID system.
Link Friends: Users can enter a friend's unique ID to "link" calendars, merging social connections with schedule management.
Manage Connections: Users have a dedicated interface to manage their list of linked friends.
3. Granular Privacy Controls Users have complete control over how they appear to others via the Profile settings:

Online Status: Toggle visibility to appear online or hidden.
Calendar Access: A master switch to grant or revoke access to your calendar data for linked friends.
Account Type: Switch between "Public" and "Private" account profiles.
Notifications: Toggle system notifications for updates and invites.
4. Communication

Integrated Chat: A "Chats" section allows users to communicate directly within the app, keeping planning conversations alongside the calendar view.
Technical Architecture
Platform

OS: Android
Language: Java
Minimum SDK: Android API 24 (implied by usage of Java 8 streams/lambdas)
Backend & Data

Firebase Authentication: Handles user identity, sign-up, and sign-in processes.
Cloud Firestore: Used as the database to store user profiles (names, emails) and likely calendar event data.
Shared Preferences: Used for local session persistence to keep users logged in across app restarts.
Navigation Structure The app utilizes a centralized BaseActivity to manage navigation, ensuring a consistent experience across three main pillars:

Home: The dashboard/calendar view.
Chats: Messaging interface.
Profile: Settings and privacy configurations.