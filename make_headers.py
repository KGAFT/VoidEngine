import os
import subprocess
import argparse
import shutil

def generate_jni_headers(source_dir, output_dir):
    """
    Recursively finds all .java files in source_dir and generates JNI headers in output_dir.
    Cleans up generated .class files.
    """
    java_files = []

    # Walk through directory and collect all .java files
    for root, _, files in os.walk(source_dir):
        for file in files:
            if file.endswith(".java"):
                java_files.append(os.path.join(root, file))

    if not java_files:
        print("No Java files found in the specified directory.")
        return

    # Ensure output directory exists
    os.makedirs(output_dir, exist_ok=True)

    # Construct the command to generate JNI headers
    cmd = ["javac", "-h", output_dir] + java_files

    try:
        subprocess.run(cmd, check=True)
        print(f"JNI headers generated in {output_dir}")

        # Remove generated .class files
        for java_file in java_files:
            class_file = java_file.replace(".java", ".class")
            if os.path.exists(class_file):
                os.remove(class_file)
                print(f"Removed {class_file}")
    except subprocess.CalledProcessError as e:
        print(f"Error while generating JNI headers: {e}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate JNI headers for Java files.")
    parser.add_argument("source_dir", help="Directory containing Java source files.")
    parser.add_argument("output_dir", help="Directory to store generated JNI headers.")

    args = parser.parse_args()
    source_dir = "src/main/java/com/kgaft/VoidEngine/JNI"
    output_dir = "jni_include/"
    generate_jni_headers(args.source_dir, args.output_dir)
    shutil.copytree("../modules/VulkanLib-java/src/main/java/com/kgaft/VulkanLib", "../src/main/java/com/kgaft/VulkanLib")