import React, { Component } from 'react';
import './OrderInfo.css';

class OrderInfo extends Component {

    render() {
        const { orderId, productName, orderPrice, orderColor, orderSize, orderQuantity, productThumbnailName } = this.props.order;
        const className = this.props.thisOrder ? "orderBold" : "orderLight";
        return (
            <tr className={className}>
                <td>{orderId}</td>
                <td><div><img src={"https://storage.googleapis.com/bit-jaehoon/"+productThumbnailName}/></div>
                <div>{productName}</div></td>
                <td>{orderColor}</td>
                <td>{orderSize}</td>
                <td>{orderQuantity}</td>
                <td>{orderPrice}</td>
            </tr>
        )
    }
}

export default OrderInfo;