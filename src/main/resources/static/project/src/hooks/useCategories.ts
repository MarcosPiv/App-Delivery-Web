import { useState, useEffect } from 'react';
import { sampleCategories } from '../data/sampleData';

export const useCategories = () => {
    const [categories, setCategories] = useState(sampleCategories);

    // In a real application, you would fetch categories from an API
    useEffect(() => {
        setCategories(sampleCategories);
    }, []);

    return { categories };
};