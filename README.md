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

# Use Customer Pulse Survey page
add customer pulse as a stand alone page by calling method showSurveyPage with parameter context, option, params, closeDelayInMs

```bash
CustomerPulseSurvey.showSurveyPage(context, option, params, closeDelayInMs)
```

# Use Customer Pulse Survey bottom sheet dialog
add customer pulse as a bottom sheet dialog by calling method showSurveyBottomSheet with parameter context, option, params, dismissible, closeDelayInMs

```bash
CustomerPulseSurvey.showSurveyPage(context, option, params, closeDelayInMs)
```


