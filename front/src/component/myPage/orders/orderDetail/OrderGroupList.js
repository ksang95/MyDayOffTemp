import React, {Component} from 'react';
import OrderInfo from './OrderInfo';

class OrderGroupList extends Component{
    static defaultProps={
        data:[]
    }

    render(){
        const {data}=this.props;
        const list = data.map(
            (order,index) => {
                const thisOrder=order.orderId===this.props.orderId&&true;
                return (
                    <OrderInfo key={order.orderId} order={order} thisOrder={thisOrder}/>
                )
            }
        );

        return (
            <div>
                주문 내역
            <table>
                <thead><tr><th>번호</th><th>상품명</th><th>색상</th><th>사이즈</th><th>수량</th><th>주문가격</th></tr></thead>
                <tbody>{list}</tbody>
            </table>
            </div>
        )
    }
}

export default OrderGroupList;