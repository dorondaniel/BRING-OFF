import sys
import joblib
import os
def main(input_data):
    # Convert input data to a list of floats
    input_data = [float(i) for i in input_data]

    # Get the absolute path to the model file
    model_path = os.path.join(os.path.dirname(__file__), 'gradientboostingmodel.pkl')

    # Load the model from the file
    model = joblib.load(model_path)

    # Make predictions
    prediction = model.predict([input_data])

    # Output the prediction
    print(prediction[0])

if __name__ == "__main__":
    main(sys.argv[1:])
