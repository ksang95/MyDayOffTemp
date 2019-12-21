import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import UserAnalysis from './component/admin/analysis/UserAnalysis';
import UpdateInvoice from './component/admin/orders/updateInvoice';
import DeleteProduct from './component/admin/product/DeleteProduct';
import Menu from './component/admin/product/Menu';
import ProductForm from './component/admin/product/ProductForm';
import UserAxios from './component/admin/userList/UserAxios';
import Withdraw from './component/common/login/Withdraw';
import SignUp from './component/common/login/SignUp';
class App extends Component {
  render() {

    return (
      <div>
        <Route path="/" component={Menu} />
        <Switch>
          <Route path="/admin/updateInvoice" component={UpdateInvoice} />
          <Route path="/admin/addProduct" component={ProductForm} />
          <Route path="/admin/deleteProduct" component={DeleteProduct} />
          <Route path="/admin/userList" component={UserAxios} />
          <Route path="/admin/usersAnalysis" component={UserAnalysis} />
          <Route path="/withdraw" component={Withdraw} />
          <Route path="/signUp" component={SignUp}/>
        </Switch>
      </div>
    );
  }
}

export default App;
