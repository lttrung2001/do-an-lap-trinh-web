// MenuToggle
let toggle=document.querySelector('.toggle')
let mainMenu=document.querySelector('.mainMenu')
let navigation=document.querySelector('.navigation')
let main=document.querySelector('.main')
let logo=document.querySelector('.logoManagement')
let logoImg=document.querySelector('.imgLogoManagement')
toggle.addEventListener('click',function(){
    mainMenu.classList.toggle('active')
    logo.classList.toggle('hideTitle')
    logoImg.classList.toggle('zoomOut')
})




// keep hover
let listMenu=document.querySelectorAll('.navigation li')
function activeLink(){
    listMenu.forEach((item)=> 
        item.classList.remove('hovered'))

        this.classList.add('hovered')
}
listMenu.forEach((item)=>
    item.addEventListener('mouseover',activeLink))

// end keep hover


// Login admin
	
	function showPass(){
		var x=document.getElementById('pass')
		console.log('askljdjhaskldjsa;')
		if (x.type === "password") {
	    	x.type = "text";
		} 
		else {
	    	x.type = "password";
  		}
		
	}
// End of login admin

// Order Managerment
    let lineTableOrder=document.querySelectorAll('.orderList table tbody tr')
    for(let i=0; i<lineTableOrder.length; i++){
        if(i%2!=0){
            lineTableOrder[i].classList.add('colorLine')
        }
    }

// End of Managerment



// Product Management
    
 /*   let inputSearchProduct=document.querySelector('.searchProduct input')
    let iconSearchProduct=document.querySelectorAll('.mainProduct .topMainProduct .searchProduct span')
    let demHoverSearchQLSP=0
    for(let i=0; i<iconSearchProduct.length; i++){
        iconSearchProduct[i].addEventListener('mouseenter',function(){
            document.querySelector('.mainProduct .topMainProduct .searchProduct span:first-child').style.width='60%'
        })
        iconSearchProduct[i].addEventListener('mouseleave',function(){
            if(demHoverSearchQLSP==0){
                document.querySelector('.mainProduct .topMainProduct .searchProduct span:first-child').style.width='0'
            }
        })
    }*/
    // iconSearchProduct.addEventListener('click',function(){
    //     iconSearchProduct.style.right=0
    //     inputSearchProduct.style.width='300px'
    // })
/*    inputSearchProduct.addEventListener('keypress',function(){
        demHoverSearchQLSP=1
    })*/
    

// End of Product Management

// Customer Managerment

    memberListCM=document.querySelectorAll('.cardCustomer')
    cardDetailCM=document.querySelector('.cardInforCM')
    nameMemberListCM=document.querySelectorAll('.cardCustomer .nameCus h2')
    idMemberListCM=document.querySelectorAll('.cardCustomer .nameCus div')
    addMemberListCM=document.querySelectorAll('.cardCustomer .extendInfo .address span')
    mailMemberListCM=document.querySelectorAll('.cardCustomer .extendInfo .mail span')
    phoneMemberListCM=document.querySelectorAll('.cardCustomer .extendInfo .call span')
    passMemberListCM=document.querySelectorAll('.cardCustomer .extendInfo .pass span')
    pointMemberListCM=document.querySelectorAll('.cardCustomer .extendInfo .point span')
    for(let i=0; i<memberListCM.length; i++){
        memberListCM[i].addEventListener('click',function(e){        
            // document.getElementById('facePage').classList.add('filter')
            cardDetailCM.style.display='flex'  
			document.querySelector('.cardInforCM h3').innerHTML=nameMemberListCM[i].innerHTML
            document.querySelector('.addressCard span').innerHTML=addMemberListCM[i].textContent
            document.querySelector('.phoneCard span').innerHTML=phoneMemberListCM[i].textContent
            document.querySelector('.mailCard span').innerHTML=mailMemberListCM[i].textContent
            document.querySelector('.userCard span').innerHTML=idMemberListCM[i].textContent
            document.querySelector('.passwordCard span').innerHTML=passMemberListCM[i].textContent
            document.querySelector('.pointCard span').innerHTML=pointMemberListCM[i].textContent
        })
    }
    function closeCardInfo(){
	cardDetailCM.style.display='none'
	}
// End o fCustomer Managerment

// Statistical : Thống kê

//    let mainStatistical=document.querySelector('.mainStatistical')
//    let mainCustomerManagement=document.querySelector('.mainCM')
//    let mainProduct=document.querySelector('.mainProduct')
//    let mainOrder=document.querySelector('.mainOrder')
//    let mainTypeProduct=document.querySelector('.mainTypeProduct')
//    for(let i=0; i<listMenu.length; i++){
//        listMenu[i].addEventListener('click',function(){
//            if(i==5){
//                mainStatistical.style.display='block'    
//            }
//            else{
//                mainStatistical.style.display='none'
//            }
//            if(i==4){
//                mainCustomerManagement.style.display='flex'
//                
//            }
//            else{
//                mainCustomerManagement.style.display='none'
//            }
//            if(i==3){
//                mainProduct.style.display='block'
//            }
//            else{
//                mainProduct.style.display='none'
//            }
//            if(i==2){
//                mainTypeProduct.style.display='block'
//            }
//            else{
//                mainTypeProduct.style.display='none'
//            }
//            if(i==1){
//                mainOrder.style.display='block'
//            }
//            else{
//                mainOrder.style.display='none'
//            }
//
//        })
//    }
    // hoverStatistical.addEventListener('mouseover',function(){
    //     mainStatistical.style.display='block'
    // })
// End of Statistical