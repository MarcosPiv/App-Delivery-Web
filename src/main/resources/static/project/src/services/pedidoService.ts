import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

// Obtiene todos los pedidos y retorna un array de Pedido
export const getPedidos = async (): Promise<any[]> => {
    try {
        const response = await axios.get<any[]>(`${BASE_URL}/pedido/mostrarTodos`);
        return response.data;
    } catch (error) {
        console.error('Error fetching orders:', error);
        throw error;
    }
};

// Obtiene un pedido por ID
export const getPedidoById = async (id: number): Promise<any> => {
    try {
        const response = await axios.get<any>(`${BASE_URL}/pedido/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching order by ID:', error);
        throw error;
    }
};

// Crea un nuevo pedido
export const createPedido = async (data: Omit<any, 'id'>): Promise<any> => {
    try {
        const response = await axios.post<any>(`${BASE_URL}/pedidos`, data);
        return response.data;
    } catch (error) {
        console.error('Error creating order:', error);
        throw error;
    }
};

// Actualiza un pedido existente
export const updatePedido = async (id: number, data: Partial<any>): Promise<any> => {
    try {
        const response = await axios.put<any>(`${BASE_URL}/pedido/modificarPedido/${id}`, data);
        return response.data;
    } catch (error) {
        console.error('Error updating order:', error);
        throw error;
    }
};

// Elimina un pedido
export const deletePedido = async (id: number): Promise<void> => {
    try {
        await axios.delete(`${BASE_URL}/pedido/borrarPedido/${id}`);
    } catch (error) {
        console.error('Error deleting order:', error);
        throw error;
    }
};