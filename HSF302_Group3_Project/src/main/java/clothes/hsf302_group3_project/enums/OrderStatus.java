package clothes.hsf302_group3_project.enums;

public enum OrderStatus {
    PENDING,              // Chờ xác nhận
    CONFIRMED,            // Người bán xác nhận
    SHIPPING,             // Đơn hàng đang được giao
    DELIVERED,            // Đã giao hàng
    COMPLETED,            // Người mua xác nhận hoàn tất
    DELIVERY_FAILED,      // Giao hàng thất bại
    CANCELLED,            // Đơn bị hủy

    /*
    * Người dùng đặt hàng => pending
    * Admin phê duyệt => confirmed
    * Admin sau khi phê duyệt => canceled / hoặc giao cho shipper => shipping
    * Shipper giao hàng thành công => delivered / hoặc giao thất bại => delivery_failed
    * Người dùng xác nhận đã nhận hàng => completed (nếu không thì sẽ tự xác nhận sau 7 ngày kể từ khi shipper giao thành công)
    * */
}