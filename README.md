# BRING OFF

Implementation of Image classification on Android Mobile Devices using three input methods.
- Live Feed from Camera
- Image captured by Camera
- Image loaded from memory

Model used MobileNetV2 (https://github.com/neavepaul/Android-Vision)

# Mobile Computational Offloading using Machine Learning Techniques

In our innovative project, we address the challenge of resource-intensive tasks on mobile devices by leveraging machine learning techniques for computational offloading. The goal is to enhance user experience while optimizing resource utilization. Here’s how we achieve this:

## Evaluation Parameters:

We monitor several critical parameters to make informed decisions about task offloading:
CPU Percentage: By tracking CPU utilization, we determine when the CPU load reaches a threshold (e.g., 80% or 90%). When it does, we intelligently offload tasks to maintain smooth performance.
RAM Percentage: Efficient memory management is crucial. When RAM usage approaches its limit, we consider offloading tasks to free up memory.
Battery Level: To balance performance and energy efficiency, we factor in the battery level. Low battery prompts local execution, while a charged battery allows for offloading.
Network Strength (in dBm): Strong network signals facilitate offloading, minimizing latency. Weak signals may favor local execution.

## Gradient Boosting Algorithm and AI Object Detection:

Our solution employs a gradient boosting algorithm to predict optimal offloading scenarios. By learning from historical data, it adapts to varying conditions.
In our system, the core functionality revolves around the intelligent offloading mechanism. Built using the Android Vision library (https://github.com/neavepaul/Android-Vision), it evaluates tasks for offloading suitability.
When the combined evaluation parameters align favorably, we seamlessly offload tasks to a robust infrastructure, ensuring high-level user experience.
