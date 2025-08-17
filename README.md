# ToDo App

A simple To-Do list application built with Java Swing GUI.

## Features

- **Add Tasks**: Create new tasks with categories and priority levels
- **Mark Complete**: Toggle tasks between completed and pending
- **Delete Tasks**: Remove individual tasks or clear all
- **Priority Tasks**: Mark important tasks (displayed in red)
- **Categories**: Organize tasks by Personal, Work, Shopping, etc.
- **Task Counter**: Shows total, completed, and pending tasks

## GUI Components Used

- `JFrame` - Main application window
- `JTextField` - Task input field
- `JButton` - Add, Delete, Clear All, Toggle Complete buttons
- `JList` - Display tasks
- `JCheckBox` - Priority marking
- `JComboBox` - Category selection

## Requirements

- Java 8 or higher
- IntelliJ IDEA CE, Eclipse, or any Java IDE
- No external libraries needed (uses built-in Swing)

## How to Run

### Using IDE (IntelliJ/Eclipse):
1. Create new Java project
2. Copy code to `ToDoApp.java`
3. Run the main method

### Using Command Line:
```bash
javac ToDoApp.java
java ToDoApp
```

## How to Use

1. **Add Task**: Type description, select category, check priority if needed, click "Add Task"
2. **Complete Task**: Select task and click "Toggle Complete" (or double-click)
3. **Delete Task**: Select task and click "Delete Selected"
4. **Clear All**: Click "Clear All" to remove all tasks

## Features

- Color-coded task display (red for priority, gray for completed)
- Confirmation dialogs for deletions
- Duplicate task prevention
- Keyboard shortcuts (Enter to add tasks)
- Task timestamps

## Author

Created as a demonstration of Java Swing GUI programming.
