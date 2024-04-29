/**
 * Created at : 4/26/2024
 */
class BinaryTreeNode {
    String question;
    BinaryTreeNode yesNode;
    BinaryTreeNode noNode;

    public BinaryTreeNode(String question) {
        this.question = question;
        this.yesNode = null;
        this.noNode = null;
    }
}

public class InterviewBinaryTree {
    private BinaryTreeNode root;

    // Constructor to initialize the root of the binary tree
    public InterviewBinaryTree(BinaryTreeNode root) {
        this.root = root;
    }

    // Method to conduct the interview
    public void conductInterview() {
        traverse(root);
    }

    private void traverse(BinaryTreeNode node) {
        if (node != null) {
            System.out.println(node.question);
            boolean userResponse = getUserResponse(); // Replace with actual user input logic
            if (userResponse) {
                traverse(node.yesNode);
            } else {
                traverse(node.noNode);
            }
        }
    }

    // Simulated method to get user response
    private boolean getUserResponse() {
        // Assume getting user response logic here
        // For demonstration purposes, returning true or false randomly
        return Math.random() < 0.5;
    }

    public static void main(String[] args) {
        // Create binary tree for general medical history
        BinaryTreeNode rootMedicalHistory = new BinaryTreeNode("Do you have any medical conditions?");
        rootMedicalHistory.yesNode = new BinaryTreeNode("Do you take any medications regularly?");
        rootMedicalHistory.noNode = new BinaryTreeNode("Do you have a family history of any medical conditions?");

        // Create binary tree for blood type questions
        BinaryTreeNode rootBloodType = new BinaryTreeNode("Do you know your blood type?");
        rootBloodType.yesNode = new BinaryTreeNode("Do you know your Rh factor?");
        rootBloodType.noNode = new BinaryTreeNode("Would you like to donate blood?");

        InterviewBinaryTree medicalHistoryTree = new InterviewBinaryTree(rootMedicalHistory);
        InterviewBinaryTree bloodTypeTree = new InterviewBinaryTree(rootBloodType);

        System.out.println("Conducting medical history interview:");
        medicalHistoryTree.conductInterview();

        System.out.println("\nConducting blood type interview:");
        bloodTypeTree.conductInterview();
    }
}

