import axios from 'axios';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Login from '../../common/login/Login';

class Menu extends Component {
    state={
        login:false
    }

    async getUser(){
        const response= await axios.get("/getUser");
        if(response.data){
            sessionStorage.setItem("userId",response.data.userId);
            sessionStorage.setItem("userRole",response.data.userRole);
            this.setState({
                login:true
            })
        }else{
            this.setState({
                login:false
            })
        }
       
    }

    componentDidMount(){
        if(!sessionStorage.getItem("userId")){
            this.getUser();
        }
        else{
            this.setState({
                login:true
            })
        }
    }

    shouldComponentUpdate(nextProps, nextState){
        return nextState.login!==this.state.login;
    }


    handleLogin = (e) => {
        switch (e.target.innerHTML) {
            case '로그아웃':
                this.logout();
                break;
            case '로그인':
                document.getElementById("loginFrame").style.visibility = "visible";
                break;
        }
    }

    logout=async ()=>{
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("userRole");
        this.setState({
            login:false
        })
        const res = await axios.get("/logout")
        console.log(res.data)

        if(res.data===1){
            this.props.history.push("/")
        }
    }
    

    render() {
        const login=sessionStorage.getItem("userId")?'로그아웃':'로그인';
        return (
            <div>

                <ul>
                    <li><div onClick={this.handleLogin}>{login}</div></li>
                    <li><Link to="/signUp">회원가입</Link></li>
                    <li><Link to="/admin/addProduct">상품 등록</Link></li>
                    <li><Link to="/admin/deleteProduct/1">상품 삭제</Link></li>
                    <li><Link to="/admin/updateInvoice">송장번호 등록</Link></li>
                    <li><Link to="/admin/userList">유저리스트</Link></li>
                    <li><Link to="/admin/usersAnalysis">회원 통계</Link></li>
                    <li><Link to="/admin/ordersAnalysis">매출 통계</Link></li>
                    <li><Link to="/orderDetail/1?orderId=2">주문 상세정보</Link></li>
                    <li><Link to={{
                        pathname:"/refundRequest",
                        state:{
                            orderCount:2, //해당 order가 있는 orderGroup에 속한 order개수 넘겨주시오
                            orderView:{
                                userId:1,
                                userName:'김상희',
                                groupId:1,
                                orderId:1,
                                orderColor:1,
                                orderSize:'S',
                                orderQuantity:1,
                                productId:1,
                                productName:'jp259 귀여운 페이크퍼 카라장식의 빵빵한 웰론충전재 숏패딩점퍼 jumper',
                                productThumbnailName:'gs://my_products/jp259_red1.jpg',
                                orderDate:'2018-11-18',
                                orderPrice:42000,
                                gradeDiscount:420,
                                couponDiscount:3500,
                                pointUse:1000,
                                totalPay:65080,
                                invoice:1,
                                storesId:1,
                                deliverId:null,
                                code:'0001',
                                codeContent:'배송준비중'
                            }
                        }
                    }}>환불 신청</Link></li>
                    <li><Link to="/withdraw">탈퇴하기</Link></li>

                </ul>
                <div className="loginFrame" id="loginFrame" style={{ visibility: "hidden" }}>
                    <Login></Login>
                </div>

            </div>

        );
    }
}

export default Menu;