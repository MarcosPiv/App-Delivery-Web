import React, { useState } from 'react';
import { Trash2 } from 'lucide-react';

interface OrderDetail {
    id: number;
    cantidad: number;
    precio: number;
    itemMenuId: number;
}

interface OrderFormProps {
    onSubmit: (data: any) => void;
    initialData?: any;
    onCancel: () => void;
}

const OrderForm = ({ onSubmit, initialData, onCancel }: OrderFormProps) => {
    const [orderDetails, setOrderDetails] = useState<OrderDetail[]>(initialData?.detalles || []);
    const [newDetail, setNewDetail] = useState({
        cantidad: '',
        precio: '',
        itemMenuId: ''
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const formData = new FormData(e.target as HTMLFormElement);
        const mainData = Object.fromEntries(formData.entries());
        const completeData = {
            ...mainData,
            detalles: orderDetails
        };
        onSubmit(completeData);
    };

    const handleAddDetail = () => {
        if (newDetail.cantidad && newDetail.precio && newDetail.itemMenuId) {
            setOrderDetails([
                ...orderDetails,
                {
                    id: orderDetails.length + 1,
                    cantidad: Number(newDetail.cantidad),
                    precio: Number(newDetail.precio),
                    itemMenuId: Number(newDetail.itemMenuId)
                }
            ]);
            setNewDetail({ cantidad: '', precio: '', itemMenuId: '' });
        }
    };

    const handleRemoveDetail = (id: number) => {
        setOrderDetails(orderDetails.filter(detail => detail.id !== id));
    };

    const calculateTotal = () => {
        return orderDetails.reduce((sum, detail) => sum + (detail.cantidad * detail.precio), 0);
    };

    return (
        <div className="max-h-[80vh] overflow-y-auto px-4">
            <form onSubmit={handleSubmit} className="space-y-6">
                <div className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Cliente ID</label>
                        <input
                            type="number"
                            name="clienteId"
                            defaultValue={initialData?.clienteId}
                            className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Vendedor ID</label>
                        <input
                            type="number"
                            name="vendedorId"
                            defaultValue={initialData?.vendedorId}
                            className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Estado</label>
                        <select
                            name="estado"
                            defaultValue={initialData?.estado || 'PENDIENTE'}
                            className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                            required
                        >
                            <option value="PENDIENTE">PENDIENTE</option>
                            <option value="RECIBIDO">RECIBIDO</option>
                        </select>
                    </div>
                </div>

                <div className="border-t border-gray-200 dark:border-gray-700 pt-4">
                    <h3 className="text-lg font-medium text-gray-900 dark:text-gray-100 mb-4">Agregar Detalle del Pedido</h3>
                    <div className="grid grid-cols-3 gap-4 mb-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Cantidad</label>
                            <input
                                type="number"
                                value={newDetail.cantidad}
                                onChange={(e) => setNewDetail({ ...newDetail, cantidad: e.target.value })}
                                className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Precio</label>
                            <input
                                type="number"
                                value={newDetail.precio}
                                onChange={(e) => setNewDetail({ ...newDetail, precio: e.target.value })}
                                className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Item Menu ID</label>
                            <input
                                type="number"
                                value={newDetail.itemMenuId}
                                onChange={(e) => setNewDetail({ ...newDetail, itemMenuId: e.target.value })}
                                className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                            />
                        </div>
                    </div>
                    <button
                        type="button"
                        onClick={handleAddDetail}
                        className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600 transition-colors"
                    >
                        Agregar Detalle
                    </button>
                </div>

                <div className="mt-4">
                    <h3 className="text-lg font-medium text-gray-900 dark:text-gray-100 mb-4">Detalles del Pedido</h3>
                    <div className="max-h-[300px] overflow-y-auto border dark:border-gray-700 rounded-lg">
                        <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                            <thead className="bg-gray-50 dark:bg-gray-800 sticky top-0">
                            <tr>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">#</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Cantidad</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Precio</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Item Menu ID</th>
                                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wider">Acciones</th>
                            </tr>
                            </thead>
                            <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
                            {orderDetails.map((detail, index) => (
                                <tr key={detail.id}>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-300">{index + 1}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-300">{detail.cantidad}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-300">{detail.precio}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-300">{detail.itemMenuId}</td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-300">
                                        <button
                                            type="button"
                                            onClick={() => handleRemoveDetail(detail.id)}
                                            className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                                        >
                                            <Trash2 className="h-5 w-5" />
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="mt-4">
                    <p className="text-lg font-medium text-gray-900 dark:text-gray-100">
                        Precio Total: ${calculateTotal()}
                    </p>
                    <input
                        type="hidden"
                        name="precioTotal"
                        value={calculateTotal()}
                    />
                </div>

                <div className="flex justify-end space-x-2 mt-6 sticky bottom-0 bg-white dark:bg-gray-800 py-4">
                    <button
                        type="button"
                        onClick={onCancel}
                        className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 dark:bg-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-200 dark:hover:bg-gray-600"
                    >
                        Cancelar
                    </button>
                    <button
                        type="submit"
                        className="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700"
                    >
                        {initialData ? 'Guardar Cambios' : 'Agregar Pedido'}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default OrderForm;