// Sample data for testing
export const sampleVendors = [
  {
    id: 1,
    nombre: 'Juan Pérez',
    direccion: 'Av. Corrientes 1234',
    latitud: -34.603722,
    longitud: -58.381592
  }
];

export const sampleClients = [
  {
    id: 1,
    nombre: 'María González',
    direccion: 'Av. Santa Fe 4321',
    email: 'maria@email.com',
    cuit: '20-12345678-9',
    latitud: -34.595862,
    longitud: -58.383759
  }
];

export const sampleMenuItems = [
  {
    id: 1,
    nombre: 'Pizza Margherita',
    descripcion: 'Pizza clásica con tomate y mozzarella',
    precio: 1500,
    categoria: 'Pizzas',
    peso: 800,
    tipo: 'Comida',
    graduacion: 0,
    tamaño: 'Grande',
    calorias: 1200,
    aptoVegano: false,
    aptoCeliaco: false,
    pesoSinEnvase: 750
  }
];

export const sampleOrders = [
  {
    id: 1,
    clienteId: 1,
    vendedorId: 1,
    precioTotal: 1500,
    estado: 'PENDIENTE'
  }
];

export const sampleCategories = [
  {
    id: 1,
    descripcion: 'Pizzas',
    tipo: 'Comida'
  }
];