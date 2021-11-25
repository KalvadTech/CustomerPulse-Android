# CustomerPulseSurveyAndroid
The Customer Pulse Android library and Example of how to use it

# CustomerPulseSurvey
is a module written in java used to implement Customer Pulse Survey in android applications.


## Installation

add file CustomerPulseSurvey.aar to your libs directory in your android project and make sure your libs folder is implemented in your project dependencies as JAR/AAR dependencies.


## Usage

# Parameters
<table>

  <tr>
      <td>context</td>
      <td>context of activity you trying to call from</td>
  </tr>
  
  <tr>
      <td>option</td>
      <td>String: holds the linking_id or the token provided by the Customer Pulse Survey provider</td>
  </tr>
  
  <tr>
      <td>params</td>
      <td>HashMap<String, String>: holds all the parmaters needed by the Customer Pulse Survey provider such as lang</td>
  </tr>
  
  <tr>
      <td>closeDelayInMs</td>
      <td>Integer: time to wait before closing the survey after finish in milli seconds</td>
  </tr>
  
  <tr>
      <td>dismissible</td>
      <td>Boolean: used only for bottom sheet to enable or disable closing the survey by the user</td>
  </tr>
</table>

to set language add language code to hashmap with key lang

```bash
  HashMap<String, String> params = new HashMap<>();
  params.put("lang", "ar");
```

# Use Customer Pulse Survey page
add customer pulse as a stand alone page by calling method showSurveyPage(Context context, String option, HashMap<String, String> params, int closingDelayInMs)

```bash
CustomerPulseSurvey.showSurveyPage(context, option, params, closeDelayInMs)
```

<table>

  <td> <img src="https://user-images.githubusercontent.com/24971915/143430605-4ca77e06-8b71-4932-8f2a-84bfdd4304c7.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143430887-fd966724-ec4e-48d9-8df2-bef42367c9c7.png" width="200" /></td>
  
</table>



# Use Customer Pulse Survey bottom sheet dialog
add customer pulse as a bottom sheet dialog by calling method showSurveyBottomSheet(Context context, String option, HashMap<String, String> params, boolean dismissible, int closingDelayInMs)

```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, option, params, dismissible, closeDelayInMs)
```

to disable user closing bottom sheet set dismissible to false

```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, option, params, false, closeDelayInMs)
```
<table>
  <td> <img src="https://user-images.githubusercontent.com/24971915/143431354-d8eecceb-ab00-4fe5-b7f7-1ebb68ab6193.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143431360-59e15169-a892-415d-ba5f-894f866fa8e6.png" width="200" /></td>
</table>


to enable user closing bottom sheet set dismissible to true

```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, option, params, true, closeDelayInMs)
```
<table>
  <td> <img src="https://user-images.githubusercontent.com/24971915/143432252-53618c6e-c9c3-450a-a68e-5285bb05f3c1.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143432266-3bf7c603-6871-470e-9396-28caccde6f43.png" width="200" /></td>
</table>

