import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoApp extends JFrame {
  
    private JTextField taskInputField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton clearAllButton;
    private JButton markCompleteButton;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JLabel statusLabel;
    private JLabel totalTasksLabel;
    private JCheckBox priorityCheckBox;
    private JComboBox<String> categoryComboBox;
    
    private static class Task {
        private String description;
        private boolean isCompleted;
        private String dateAdded;
        private boolean isPriority;
        private String category;
        
        public Task(String description, boolean isPriority, String category) {
            this.description = description;
            this.isCompleted = false;
            this.isPriority = isPriority;
            this.category = category;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            this.dateAdded = sdf.format(new Date());
        }
        
        public String getDescription() { return description; }
        public boolean isCompleted() { return isCompleted; }
        public void setCompleted(boolean completed) { this.isCompleted = completed; }
        public String getDateAdded() { return dateAdded; }
        public boolean isPriority() { return isPriority; }
        public String getCategory() { return category; }
        
        @Override
        public String toString() {
            String status = isCompleted ? "✓" : "○";
            String priority = isPriority ? "[!] " : "";
            return String.format("%s %s%s (%s) - %s", status, priority, description, category, dateAdded);
        }
    }
    
    public ToDoApp() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setFrameProperties();
        updateStatusBar();
    }
    
    private void initializeComponents() {
        
        taskInputField = new JTextField(20);
        taskInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        taskInputField.setBorder(BorderFactory.createCompoundBorder(
            taskInputField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
   
        addButton = new JButton("Add Task");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setEnabled(false);
        
        clearAllButton = new JButton("Clear All");
        clearAllButton.setBackground(new Color(156, 39, 176));
        clearAllButton.setForeground(Color.WHITE);
        clearAllButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        markCompleteButton = new JButton("Toggle Complete");
        markCompleteButton.setBackground(new Color(255, 152, 0));
        markCompleteButton.setForeground(Color.WHITE);
        markCompleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        markCompleteButton.setEnabled(false);
      
        priorityCheckBox = new JCheckBox("High Priority");
        priorityCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
   
        String[] categories = {"Personal", "Work", "Shopping", "Health", "Education", "Other"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
       
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskCellRenderer());
    
        statusLabel = new JLabel("Ready to add tasks!");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        statusLabel.setForeground(Color.GRAY);
        
        totalTasksLabel = new JLabel("Total Tasks: 0");
        totalTasksLabel.setFont(new Font("Arial", Font.BOLD, 11));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
       
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(33, 150, 243));
        JLabel titleLabel = new JLabel("My ToDo List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));
        titlePanel.add(titleLabel);
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
       
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(taskInputField, gbc);
        
        // Priority checkbox
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        inputPanel.add(priorityCheckBox, gbc);
        
        // Category dropdown
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(new JLabel("Category:"), gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        inputPanel.add(categoryComboBox, gbc);
        
        // Add button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(addButton, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(markCompleteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearAllButton);
        
        // List Panel
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tasks"));
        
        // Status Panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusPanel.setBackground(new Color(245, 245, 245));
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(totalTasksLabel, BorderLayout.EAST);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Add all panels to frame
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.PAGE_END);
    }
    
    private void setupEventListeners() {
        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        
        // Enter key in text field
        taskInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        
        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedTask();
            }
        });
        
        // Clear all button action
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllTasks();
            }
        });
        
        // Mark complete button action
        markCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTaskComplete();
            }
        });
        
        // List selection listener
        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        // Double-click to toggle completion
        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    toggleTaskComplete();
                }
            }
        });
    }
    
    private void addTask() {
        String taskText = taskInputField.getText().trim();
        
        if (taskText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a task description!", 
                "Empty Task", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check for duplicate tasks
        for (int i = 0; i < listModel.getSize(); i++) {
            if (listModel.getElementAt(i).getDescription().equalsIgnoreCase(taskText)) {
                JOptionPane.showMessageDialog(this, 
                    "This task already exists!", 
                    "Duplicate Task", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        boolean isPriority = priorityCheckBox.isSelected();
        String category = (String) categoryComboBox.getSelectedItem();
        
        Task newTask = new Task(taskText, isPriority, category);
        
        // Add priority tasks at the top
        if (isPriority) {
            listModel.add(0, newTask);
        } else {
            listModel.addElement(newTask);
        }
        
        // Clear input fields
        taskInputField.setText("");
        priorityCheckBox.setSelected(false);
        categoryComboBox.setSelectedIndex(0);
        
        // Focus back to input field
        taskInputField.requestFocus();
        
        updateStatusBar();
        statusLabel.setText("Task added successfully!");
    }
    
    private void deleteSelectedTask() {
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex != -1) {
            Task selectedTask = listModel.getElementAt(selectedIndex);
            
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete:\n\"" + selectedTask.getDescription() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                listModel.remove(selectedIndex);
                updateStatusBar();
                statusLabel.setText("Task deleted successfully!");
            }
        }
    }
    
    private void clearAllTasks() {
        if (listModel.getSize() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No tasks to clear!", 
                "Empty List", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete all " + listModel.getSize() + " tasks?",
            "Confirm Clear All",
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            listModel.clear();
            updateStatusBar();
            statusLabel.setText("All tasks cleared!");
        }
    }
    
    private void toggleTaskComplete() {
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex != -1) {
            Task selectedTask = listModel.getElementAt(selectedIndex);
            selectedTask.setCompleted(!selectedTask.isCompleted());
            
            // Refresh the list display
            taskList.repaint();
            
            updateStatusBar();
            String status = selectedTask.isCompleted() ? "completed" : "pending";
            statusLabel.setText("Task marked as " + status + "!");
        }
    }
    
    private void updateButtonStates() {
        boolean hasSelection = taskList.getSelectedIndex() != -1;
        deleteButton.setEnabled(hasSelection);
        markCompleteButton.setEnabled(hasSelection);
    }
    
    private void updateStatusBar() {
        int totalTasks = listModel.getSize();
        int completedTasks = 0;
        
        for (int i = 0; i < totalTasks; i++) {
            if (listModel.getElementAt(i).isCompleted()) {
                completedTasks++;
            }
        }
        
        totalTasksLabel.setText(String.format("Total: %d | Completed: %d | Pending: %d", 
            totalTasks, completedTasks, totalTasks - completedTasks));
    }
    
    private void setFrameProperties() {
        setTitle("ToDo List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);
        
        // Set application icon (optional)
        try {
            // You can add an icon here if you have one
            // setIconImage(new ImageIcon("icon.png").getImage());
        } catch (Exception e) {
            // Ignore if no icon
        }
    }
    
    // Custom cell renderer for better task display
    private static class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Task) {
                Task task = (Task) value;
                
                // Change appearance based on task status
                if (task.isCompleted()) {
                    setForeground(isSelected ? Color.WHITE : Color.GRAY);
                    setFont(getFont().deriveFont(Font.ITALIC));
                } else if (task.isPriority()) {
                    setForeground(isSelected ? Color.WHITE : Color.RED);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(isSelected ? Color.WHITE : Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
            }
            
            return this;
        }
    }
    
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
      
        }
        
       
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoApp().setVisible(true);
            }
        });
    }
}
