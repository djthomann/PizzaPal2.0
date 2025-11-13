# PizzaPal 2.0

PizzaPal 2.0 is a newer version of PizzaPal (available on my github). It tried rewriting the software with more and better features and a cleaner codebase and more Test Coverage.

It's still a storage organization software. In which you can build virtual storages, add shelf and packages and save these storages as files.

# Techstack

- Java 21
- JavaFX 21.0.2 (UI)
- JUnit (Testing)
- JaCoCo (Coverage)
- Jackson (File IO)
- logback/SLF4J (Logging)

# New Features

PizzaPal 2.0 includes the following new features:

- New UI: Including Main Menu, Settings and Editor Views
- Edit functionality for all Entities within a storage
- Commands for all actions taken within the storage (Move, Edit, Add and Delete)
- Undo/Redo functionality for Commands
- NotificationCenter for all things related to the storage
- Editing multiple Storages at once
- Saving and Loading Storages in JSON format
- Audio: Theme in Main Menu and AudioClips as feedback for actions taken by the user and errors

# Menues

|                                                                                |                                                                                         |
| ------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------- |
| ![Main Menu](/documentation/screenshots/main_menu.png "Main Menu")             | ![New Storage Menu](/documentation/screenshots/new_storage_menu.png "New Storage Menu") |
| ![Settings Menu](/documentation/screenshots/settings_menu.png "Settings Menu") |                                                                                         |

# Editor

![Editor](/documentation/screenshots/editor.png "Editor")

# Missing Features / What's up next

- add editing logic
- fix current test issues
- refactor Nofitication Manager
- add responsiveness
- refactor ToolState
- add a measuring tool
- add stacking items and ingredients
- add color picker
- add a help menu / shortcut info

# Music and Sound Credits

- [Main Menu Theme](https://pixabay.com/music/traditional-jazz-jazz-background-music-325355/)
- [Delete sound](https://pixabay.com/sound-effects/breaking-wooden-table-40590/)
- [Add sound](https://pixabay.com/sound-effects/pop-up-something-160353/)
- [Move sound](https://pixabay.com/sound-effects/sliding-lock-102999/)
- [Edit sound](https://pixabay.com/sound-effects/repair-metal-85833/)
- [New notification sound](https://pixabay.com/sound-effects/new-notification-04-326127/)
- [Clear notifications sound](https://pixabay.com/sound-effects/broom-sweep-106601/)
