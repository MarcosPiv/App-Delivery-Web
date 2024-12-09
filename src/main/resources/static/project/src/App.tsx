import { useState, useEffect } from 'react';
import { ThemeProvider } from './context/ThemeContext';
import Header from './components/Header';
import Navigation from './components/Navigation';
import SearchBar from './components/SearchBar';
import DataTable from './components/DataTable';
import AddButton from './components/AddButton';
import Modal from './components/Modal';
import VendorForm from './components/forms/VendorForm';
import ClientForm from './components/forms/ClientForm';
import MenuItemForm from './components/forms/MenuItemForm';
import OrderForm from './components/forms/OrderForm';
import CategoryForm from './components/forms/CategoryForm';
import {  sampleMenuItems, sampleOrders } from './data/sampleData';
import { getVendedores , deleteVendedor} from './services/vendedorService';
import { getClient, deleteClient } from './services/clienteService';
import { getCategories, deleteCategory } from './services/categoriaService';

function App() {
  const [clients, setClients] = useState<any[]>([]);
  const [vendors, setVendors] = useState<any[]>([]);
  const [categories, setCategories] = useState<any[]>([]);
  const [activeTab, setActiveTab] = useState('vendors');
  const [searchQuery, setSearchQuery] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [modalMode, setModalMode] = useState<'add' | 'edit'>('add');
  const [editingItem, setEditingItem] = useState<any>(null);

  const vendorsColumns = [
    { key: 'id', header: 'ID' },
    { key: 'nombre', header: 'Nombre' },
    { key: 'direccion', header: 'Dirección' },
    { key: 'latitud', header: 'Latitud' },
    { key: 'longitud', header: 'Longitud' }
  ];

  const clientsColumns = [
    { key: 'id', header: 'ID' },
    { key: 'nombre', header: 'Nombre' },
    { key: 'direccion', header: 'Dirección' },
    { key: 'email', header: 'Email' },
    { key: 'cuit', header: 'CUIT' },
    { key: 'latitud', header: 'Latitud' },
    { key: 'longitud', header: 'Longitud' }
  ];

  const menuColumns = [
    { key: 'id', header: 'ID' },
    { key: 'nombre', header: 'Nombre' },
    { key: 'descripcion', header: 'Descripción' },
    { key: 'precio', header: 'Precio' },
    { key: 'categoria', header: 'Categoría' },
    { key: 'peso', header: 'Peso' },
    { key: 'tipo', header: 'Tipo' },
    { key: 'graduacion', header: 'Graduación' },
    { key: 'tamaño', header: 'Tamaño' },
    { key: 'calorias', header: 'Calorías' },
    { key: 'aptoVegano', header: 'Apto Vegano' },
    { key: 'aptoCeliaco', header: 'Apto Celíaco' },
    { key: 'pesoSinEnvase', header: 'Peso Sin Envase' }
  ];

  const ordersColumns = [
    { key: 'id', header: 'ID' },
    { key: 'clienteId', header: 'Cliente ID' },
    { key: 'vendedorId', header: 'Vendedor ID' },
    { key: 'precioTotal', header: 'Precio Total' },
    { key: 'estado', header: 'Estado' }
  ];

  const categoriesColumns = [
    { key: 'id', header: 'ID' },
    { key: 'descripcion', header: 'Descripción' },
    { key: 'tipoItem', header: 'Tipo Item' }
  ];

  const filteredCategories = categories.filter((category) =>
      category.id.toString().includes(searchQuery) // Búsqueda por ID
  );

  const filteredClients = clients.filter((client) =>
      client.nombre.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const filteredVendors = vendors.filter((vendor) =>
      vendor.nombre.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const getActiveData = () => {
    switch (activeTab) {
      case 'vendors':
        return filteredVendors;
      case 'clients':
        return filteredClients;
      case 'menu':
        return sampleMenuItems;
      case 'orders':
        return sampleOrders;
      case 'categories':
        return filteredCategories;
      default:
        return [];
    }
  };

  const getModalTitle = () => {
    const action = modalMode === 'add' ? 'Agregar' : 'Editar';
    switch (activeTab) {
      case 'vendors':
        return `${action} Vendedor`;
      case 'clients':
        return `${action} Cliente`;
      case 'menu':
        return `${action} Item Menu`;
      case 'orders':
        return `${action} Pedido`;
      case 'categories':
        return `${action} Categoría`;
      default:
        return 'Modal';
    }
  };

  useEffect(() => {
    if (activeTab === 'vendors') {
      fetchVendors();
    }
    if (activeTab === 'clients') {
      fetchClients();
    }
    if (activeTab === 'categories') {
      fetchCategories();
    }
  }, [activeTab]);

  const fetchClients = async () => {
    try {
      const data = await getClient(); // Obtener los datos de los clientes
      // @ts-ignore
      const formattedData = data.map((client) => ({
        ...client,
        latitud: client.coordenada?.lat || 'N/A',
        longitud: client.coordenada?.lng || 'N/A',
      }));
      setClients(formattedData); // Actualizar el estado de los clientes con los datos formateados
    } catch (error) {
      console.error('Error fetching clients:', error);
    }
  };

  const fetchCategories = async () => {
    try {
      const data = await getCategories(); // Llama a la API para obtener las categorías
      const formattedData = data.map((category) => ({
        id: category.id,
        descripcion: category.descripcion || 'Sin descripción', // Valor predeterminado si falta
        tipoItem: category.tipoItem || 'N/A', // Valor predeterminado si falta
      }));
      setCategories(formattedData); // Actualiza el estado de categorías
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const fetchVendors = async () => {
    try {
      const data = await getVendedores();
      const formattedData = data.map((vendor) => ({
        ...vendor,
        latitud: vendor.coordenada?.lat || 'N/A',
        longitud: vendor.coordenada?.lng || 'N/A',
      }));
      setVendors(formattedData);
    } catch (error) {
      console.error('Error fetching vendors:', error);
    }
  };

  const handleDelete = async (item: { id: number }) => {
    if (window.confirm('¿Está seguro que desea eliminar este registro?')) {
      try {
        if (activeTab === 'vendors') {
          // Eliminar un vendedor
          await deleteVendedor(item.id);
          setVendors((prevVendors) =>
              prevVendors.filter((vendor) => vendor.id !== item.id)
          );
        } else if (activeTab === 'clients') {
          // Eliminar un cliente
          await deleteClient(item.id);
          setClients((prevClients) =>
              prevClients.filter((client) => client.id !== item.id)
          );
        } else if (activeTab === 'categories') {
          // Eliminar una categoría
          await deleteCategory(item.id);
          setCategories((prevCategories) =>
              prevCategories.filter((category) => category.id !== item.id)
          );
        } else {
          console.error('Tipo desconocido para eliminar:', activeTab);
        }
      } catch (error) {
        console.error(`Error eliminando registro en ${activeTab}:`, error);
      }
    }
  };


  const handleAdd = () => {
    setModalMode('add');
    setEditingItem(null);
    setShowModal(true);
  };

  const handleEdit = (item: any) => {
    setModalMode('edit');
    setEditingItem(item);
    setShowModal(true);
  };

  const handleSubmit = (data: any) => {
    console.log('Form submitted:', data);
    setShowModal(false);
    fetchVendors(); // Fetch updated data
    fetchClients();
    fetchCategories();
  };

  const renderForm = () => {
    const props = {
      onSubmit: handleSubmit,
      initialData: editingItem,
      onCancel: () => setShowModal(false)
    };

    switch (activeTab) {
      case 'vendors':
        return <VendorForm {...props} setVendors={setVendors} />;
      case 'clients':
        return <ClientForm {...props} setClients={setClients} />;
      case 'menu':
        return <MenuItemForm {...props} />;
      case 'orders':
        return <OrderForm {...props} />;
      case 'categories':
        return <CategoryForm {...props} setCategories={setCategories} />;
      default:
        return null;
    }
  };

  return (
      <ThemeProvider>
        <div className="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors">
          <Header />
          <Navigation activeTab={activeTab} setActiveTab={setActiveTab} />
          <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
            <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6">
              <div className="flex justify-between items-center mb-6">
                <SearchBar onSearch={setSearchQuery} />
                <AddButton
                    onClick={handleAdd}
                    label={`Agregar ${
                        activeTab === 'vendors' ? 'Vendedor' :
                            activeTab === 'clients' ? 'Cliente' :
                                activeTab === 'menu' ? 'Item Menu' :
                                    activeTab === 'categories' ? 'Categoría' :
                                        'Pedido'
                    }`}
                />
              </div>
              <DataTable
                  columns={
                    activeTab === 'vendors'
                        ? vendorsColumns
                        : activeTab === 'clients'
                            ? clientsColumns
                            : activeTab === 'menu'
                                ? menuColumns
                                : activeTab === 'orders'
                                    ? ordersColumns
                                    : categoriesColumns
                  }
                  data={getActiveData()}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
              />
            </div>
          </main>

          <Modal
              isOpen={showModal}
              onClose={() => setShowModal(false)}
              title={getModalTitle()}
          >
            {renderForm()}
          </Modal>
        </div>
      </ThemeProvider>
  );
}

export default App;