/*
 * Author: Rintu Mondal
 * Purpose: Auto Key board position
 * Created on: 11/06/2015
 */

$(document).ready(function(){$("textarea,input[type=text]").focusin(function(){var t=$(this).height()+15,i=428-$(this).width(),o=$(this).attr("id"),e=o.split("_");if("eng"!=e[0]&&!$(this).prop("readonly")){var n=$(this).position(),s=n.left-i;s=0>s?0:s,$("#keyBrd").css({left:s,top:n.top+t})}}).focusout(function(){})});