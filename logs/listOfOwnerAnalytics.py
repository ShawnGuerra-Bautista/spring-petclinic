import pandas as pd
from statsmodels.stats.proportion import proportions_ztest

# Importing the dataset
dataset = pd.read_csv('ListOfOwnerLog.csv')

# Get data
num_clicks = len(dataset)
num_toggle_on_clicks = dataset["toggleEnabled"].value_counts()[1]
toggle_on_click_ratio = num_toggle_on_clicks / num_clicks

zscore, pval = proportions_ztest(num_toggle_on_clicks, num_clicks, 0.5, alternative='larger')

# Print data after test
print('z-score = {:.3f}, p-value = {:}'.format(zscore, pval))
print('Reject Null Hypothesis' if pval < 0.05 else 'Don\'t Reject Null Hypothesis')
print('Direction = ' + ('Larger Ratio' if toggle_on_click_ratio > 0.5 else 'Smaller Ratio'))
print('Magnitude = ' + str(toggle_on_click_ratio))
