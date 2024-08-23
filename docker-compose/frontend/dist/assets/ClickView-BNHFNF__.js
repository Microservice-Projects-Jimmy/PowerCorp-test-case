import{d as A,u as I,m as N,r as c,o as P,a as w,p as U,q as V,c as s,b as z,e as d,f as e,F as S,g as C,i as T,t as l,j as $,k as E,l as n,s as D}from"./index-BTKqXKYf.js";const H={class:"flex flex-col items-center"},X=e("h1",{class:"my-10"},"Welcome to clicks page! Click somewhere!",-1),Y={class:"ml-10 w-full"},F={class:"relative overflow-x-auto shadow-md sm:rounded-lg"},M={class:"w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400"},R=e("caption",{class:"p-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800"}," History ",-1),q=e("thead",{class:"text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"},[e("tr",null,[e("th",{scope:"col",class:"px-6 py-3"},"Date"),e("th",{scope:"col",class:"px-6 py-3"},"X coordinate"),e("th",{scope:"col",class:"px-6 py-3"},"Y coordinate")])],-1),O={scope:"row",class:"px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"},W={class:"px-6 py-4"},G={class:"px-6 py-4"},J={class:"text-sm font-normal text-gray-900 mb-4 md:mb-0 p-3 block w-full md:inline md:w-auto"},K={class:"font-semibold text-gray-900"},Q={class:"font-semibold text-gray-900"},Z={class:"inline-flex -space-x-px rtl:space-x-reverse text-sm h-8"},ee={class:"cursor-pointer"},te=["onClick"],re=["onClick"],ae=e("li",null,[e("a",{class:"flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"},"...")],-1),oe={class:"cursor-pointer"},se=4*60*1e3,ie=A({__name:"ClickView",setup(le){const L=I(),j=N(),m=c(),b="http://localhost:8080",h=c(0),v=c(""),p=c(0);let a=0,g=10;const y=c(null),B=c(!1);let x;const f=()=>{x=setTimeout(()=>{B.value=!0,L.add({severity:"info",summary:"Info",detail:"Session expired!",life:3e3}),localStorage.clear(),j.user=null,w.push("/")},se)},u=()=>{clearTimeout(x),f()},_=r=>{y.value&&y.value.contains(r.target)||E.post(b+"/click/save",{username:localStorage.getItem("username"),XCoordinate:r.clientX,YCoordinate:r.clientY},{headers:{Authorization:`Bearer ${localStorage.getItem("token")}`}}).then(()=>{i()})},i=(r=0,o=10)=>{r=Math.max(0,r),a=r,E.get(`${b}/click/get-all/${localStorage.getItem("username")}?page=${r}&size=${o}`,{headers:{Authorization:`Bearer ${localStorage.getItem("token")}`}}).then(t=>{m.value=t.data.content,p.value=t.data.totalElements,v.value=r*o+"-"+(r+1)*o,h.value=t.data.totalPages})};return P(()=>{localStorage.getItem("token")==null&&w.push("/"),i(),window.addEventListener("click",_),f(),window.addEventListener("click",u),window.addEventListener("keydown",u)}),U(()=>{clearTimeout(x),window.removeEventListener("click",u),window.removeEventListener("keydown",u)}),V(()=>{window.removeEventListener("click",_)}),(r,o)=>(n(),s("div",H,[z(d(D)),X,e("section",Y,[e("div",F,[e("table",M,[R,q,e("tbody",null,[(n(!0),s(S,null,C(m.value,(t,k)=>(n(),s("tr",{key:k,class:"bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"},[e("th",O,l(t.clickedAt),1),e("td",W,l(t.x),1),e("td",G,l(t.y),1)]))),128))])]),e("nav",{ref_key:"excludedArea",ref:y,class:"flex items-center flex-column flex-wrap md:flex-row justify-between pt-4","aria-label":"Table navigation"},[e("span",J,[T("Showing "),e("span",K,l(v.value),1),T(" of "),e("span",Q,l(p.value),1)]),e("ul",Z,[e("li",ee,[e("a",{onClick:o[0]||(o[0]=t=>i($(a)?--a.value:--a,d(g))),class:"flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"},"Previous")]),(n(!0),s(S,null,C(h.value>5?5:h.value,t=>(n(),s("li",{class:"cursor-pointer",key:t},[t-1!=d(a)?(n(),s("a",{key:0,onClick:k=>i(t-1,d(g)),class:"flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"},l(t),9,te)):(n(),s("a",{key:1,onClick:k=>i(t-1,d(g)),"aria-current":"page",class:"flex items-center justify-center px-3 h-8 text-blue-600 border border-gray-300 bg-blue-50 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white"},l(t),9,re))]))),128)),ae,e("li",oe,[e("a",{onClick:o[1]||(o[1]=t=>i($(a)?++a.value:++a,d(g))),class:"flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"},"Next")])])],512)])])]))}});export{ie as default};
