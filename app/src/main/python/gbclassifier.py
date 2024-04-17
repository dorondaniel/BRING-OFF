import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import GradientBoostingClassifier

# Load dataset from CSV
df = pd.read_csv("offload.csv", header=None)

dataset = pd.read_csv('offload.csv')
X = dataset.iloc[:, [0, 1, 2, 3]].values
y = dataset.iloc[:, -1].values

# Split data into train and test sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20, random_state=42, stratify=y)

# Create a GradientBoostingClassifier ensemble
gb_clf = GradientBoostingClassifier(n_estimators=3, random_state=42)

# Train the GradientBoostingClassifier ensemble on the training data
gb_clf.fit(X_train, y_train)

def prediction(array){
    return gb_clf.predict([array])
}