import React, {Component} from 'react';
import '../css/login.css';

const Login = () => {
    return (
        <div className="login-container">
            <div className="login-content">
                <h1 className="login-title">Login to SpringSocial</h1>
                {/* <SocialLogin /> */}
               <SocialLogin></SocialLogin>
                <div className="or-separator">
                </div>
            </div>
        </div>
    );
}


class SocialLogin extends Component {
    render() {
        const login = () => {
            window.location.href = "https://kauth.kakao.com/oauth/authorize?client_id=&redirect_uri=http://localhost:3000/login/process/kakao&response_type=code";
        
        }
        const style = {
        display : 'block'
    }
    return (
        <div className="social-login">
            <a style={style} className="btn btn-block social-btn google" href='https://localhost:8443/oauth2/authorization/google'>
                 <img  src="/images/google.jpg"></img>Log in with Google</a>
            <a style={style} className="btn btn-block social-btn facebook" href='https://localhost:8443/oauth2/authorization/facebook'>
               <img src="/images/facebook.png"></img>Log in with Facebook</a>
               <a  className="btn btn-block social-btn kakao" href='https://localhost:8443/oauth2/authorization/kakao'>
                <img src="/images/kakao_account_login_btn_medium_wide.png" name="kakao"/>
               </a>
        </div>
    );
}
    
}

export default Login;




