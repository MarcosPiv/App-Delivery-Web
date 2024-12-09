interface CategorySelectorProps {
    categories: any[];
    defaultValue?: number;
}

const CategorySelector = ({ categories, defaultValue }: CategorySelectorProps) => {
    return (
        <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Seleccionar Categoría</label>
            <select
                name="categoriaId"
                defaultValue={defaultValue}
                className="mt-1 block w-full rounded-md border dark:border-gray-600 dark:bg-gray-700 dark:text-white shadow-sm p-2"
                required
            >
                <option value="">Seleccione una categoría</option>
                {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                        {category.descripcion}
                    </option>
                ))}
            </select>
        </div>
    );
};

export default CategorySelector;