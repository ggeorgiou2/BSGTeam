function formValidation()  
{  
var lat = document.validation.lat;  
var lng = document.validation.long;  
var area = document.validation.radius;  
var days = document.validation.days;  
var nkeywords = document.validation.keywords;  

if(lat_validation(lat))  
{  
if(lng_validation(lng))  
{  
if(area_validation(area))  
{  
if(days_validation(days))  
{   
if(nKeywords_validation(nkeywords))  
{  
}  
}    
}  
}  
}  
return false;  
}  


function lat_validation(lat)  
{  
   var numbers = /([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)/;  
   if(lat.value.match(numbers))  
   {   
   return true;  
   }  
   else  
   {  
   alert('Please input real number only');  
   document.validation.lat.focus();  
   return false;  
   }  
}   


function lng_validation(lng)  
{  
   var numbers = /([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)/;  
   if(lng.value.match(numbers))  
   {   
   return true;  
   }  
   else  
   {  
   alert('Please input real number characters only');  
   document.validation.lng.focus();  
   return false;  
   }  
}   


function area_validation(area)  
{  
   var numbers = /([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)/; 
   if(area.value.match(numbers))  
   {   
   return true;  
   }  
   else  
   {  
   alert('Please input real number characters only');  
   document.validation.area.focus();  
   return false;  
   }  
}   


function days_validation(days)  
{  
   var numbers = /^[0-9]+$/;  
   if(days.value.match(numbers))  
   {   
   return true;  
   }  
   else  
   {  
   alert('Please input numeric characters only');  
   document.validation.days.focus();  
   return false;  
   }  
}   


function nKeywords_validation(nkeywords)  
{  
   var numbers = /^[0-9]+$/;  
   if(nkeywords.value.match(numbers))  
   {   
   return true;  
   }  
   else  
   {  
   alert('Please input numeric characters only');  
   document.validation.nkeywords.focus();  
   return false;  
   }  
}   