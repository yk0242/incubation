// ==UserScript==
// @name           Force Low Quality Flash for Tampermonkey
// @version        1.0.0.20170318
// @description    Force Low Quality Flash - adapted for Tampermonkey -- based on http://userscripts.org/users/75739 - Force Low Quality Flash by Oscar Sodani
// @namespace      https://github.com/yk0242
// @author         yk0242
// @copyright      2017
// @license        MIT https://opensource.org/licenses/mit-license.php
// @grant          none
// @downloadURL    none
// @include        *
// @run-at         document-end
// ==/UserScript==

(function forceLowQualityFlash(){
  
  window.onload = function(){
    forceLowQualityFlash_act();
  };
  
  function forceLowQualityFlash_act(myDoc = window.document){
  //-- based on http://userscripts.org/users/75739 - Force Low Quality Flash by Oscar Sodani
  //-- adding if checking to prevent internal error messages
  //-- quit using "with" syntax to work with Tampermonkey
    var objs;
    
    if(document.embeds){
      for(objs = document.embeds, i = objs.length-1; i >= 0; i--){
        objs[i].setAttribute('quality', 'low');
        objs[i].parentNode.appendChild(objs[i].parentNode.removeChild(objs[i]));
      }
    }
    
    if(document.getElementsByTagName('object')){
      for(objs = document.getElementsByTagName('object'), i = objs.length-1 ; i >= 0; i--){
        for(var c = objs[i].childNodes, j = c.length-1, set = false; j >= 0; j--) {
          if((c[j].tagName == 'PARAM') && (c[j].getAttribute('name') == 'quality')) { c[j].setAttribute('value', 'low'); set = true; break; }
        }
        if(!set){
          var myPar = objs[i].appendChild(document.createElement('param'));
          myPar.setAttribute('name', 'quality');
          myPar.setAttribute('value', 'low');
        }
        objs[i].parentNode.appendChild(objs[i].parentNode.removeChild(objs[i]));
      }
    }
  }
})();
