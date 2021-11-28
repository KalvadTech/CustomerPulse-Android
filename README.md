# CustomerPulseSurveyAndroid
The Customer Pulse Android library and Example of how to use it

# CustomerPulseSurvey
is a module written in java used to implement Customer Pulse Survey in android applications.


## Installation

add file CustomerPulseSurvey.aar to your libs directory in your android project and make sure your libs folder is added in your project dependencies as JAR/AAR dependencies.

### Steps

1.add CustomerPulseSurvey.aar file to your libs folder inside app folder

<img src="https://user-images.githubusercontent.com/24971915/143434248-fb979977-4c01-48fd-b779-6c60d9ec8324.png" width="600" />

2.from file menu open project structure

<img src="https://user-images.githubusercontent.com/24971915/143434434-bacc68ef-379f-4019-9ecc-03a71f3c5196.png" width="600" />

3.in left side select Dependencies

<img src="https://user-images.githubusercontent.com/24971915/143434672-1c4dbe4e-fccb-4c25-a954-a3cf989a7bfc.png" width="600" />

4.In modules select app tab

<img src="https://user-images.githubusercontent.com/24971915/143434850-d255916d-8a02-496e-b8d3-19b4125adb39.png" width="600" />

5.In declered dependencies click on the plus "+" icons and select JAR/AAR Dependency

<img src="https://user-images.githubusercontent.com/24971915/143435226-87a088d5-f8f6-4592-8758-bf13787cbce4.png" width="600" />

5.In step 1 add your libs folder name if you follow from step 1 it should be libs

<img src="https://user-images.githubusercontent.com/24971915/143435484-69bc9f2d-294b-4329-b2df-d136e3ade528.png" width="600" />

6.Press Ok button then Apply to save change.

## Usage

### Parameters
<table>

  
  <tr>
      <td>Name</td>
      <td>Type</td>
      <td>Description</td>
      <td>Default</td>
  </tr>
  
  <tr>
      <td>context</td>
      <td>Context</td>
      <td>opened activity context</td>
      <td></td>
  </tr>
  
  <tr>
      <td>link_or_token</td>
      <td>String</td>
      <td>holds the linking_id or the token provided by the Customer Pulse Survey provider</td>
      <td></td>
  </tr>
  
  <tr>
      <td>options</td>
      <td>HashMap<String, String></td>
      <td>holds all the options needed by the Customer Pulse Survey provider such as lang</td>
      <td></td>
  </tr>
  
  <tr>
      <td>closeDelayInMs</td>
      <td>Integer</td>
      <td>time to wait before closing the survey after finish in milliseconds</td>
      <td>2000 (2 seconds)</td>
  </tr>
  
  <tr>
      <td>dismissible</td>
      <td>Boolean</td>
      <td>enable or disable closing the survey by the user</td>
      <td>true</td>    
  </tr>
</table>

to set language add language code to hashmap with key lang

```bash
  HashMap<String, String> options = new HashMap<>();
  options.put("lang", "ar");
```

### Use Customer Pulse Survey page
add customer pulse as a stand alone page by calling method showSurveyPage(Context context, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs)

using default values for dismissible and closeDelayInMs
```bash
CustomerPulseSurvey.showSurveyPage(context, link_or_token, options,)
```

to change closeDelayInMs only use
```bash
CustomerPulseSurvey.showSurveyPage(context, link_or_token, options, closeDelayInMs)
```

to change dismissible only use
```bash
CustomerPulseSurvey.showSurveyPage(context, link_or_token, options, dismissible)
```

to change both use
```bash
CustomerPulseSurvey.showSurveyPage(context, link_or_token, options, dismissible, closeDelayInMs)
```

<table>

  <td> <img src="https://user-images.githubusercontent.com/24971915/143430605-4ca77e06-8b71-4932-8f2a-84bfdd4304c7.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143430887-fd966724-ec4e-48d9-8df2-bef42367c9c7.png" width="200" /></td>
  
</table>



### Use Customer Pulse Survey bottom sheet dialog
add customer pulse as a bottom sheet dialog by calling method showSurveyBottomSheet(Context context, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs)

using default values for dismissible and closeDelayInMs
```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, link_or_token, options)
```

to change closeDelayInMs only use
```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, link_or_token, options, closingDelayInMs)
```

to change dismissible only use
```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, link_or_token, options, dismissible)
```

to change both use
```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, link_or_token, options, dismissible, closingDelayInMs)
```


<table>
  <td> <img src="https://user-images.githubusercontent.com/24971915/143431354-d8eecceb-ab00-4fe5-b7f7-1ebb68ab6193.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143431360-59e15169-a892-415d-ba5f-894f866fa8e6.png" width="200" /></td>
</table>


to enable user closing bottom sheet set dismissible to true

```bash
CustomerPulseSurvey.showSurveyBottomSheet(context, link_or_token, options, true, closeDelayInMs)
```

<table>
  <td> <img src="https://user-images.githubusercontent.com/24971915/143432252-53618c6e-c9c3-450a-a68e-5285bb05f3c1.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143432266-3bf7c603-6871-470e-9396-28caccde6f43.png" width="200" /></td>
</table>

