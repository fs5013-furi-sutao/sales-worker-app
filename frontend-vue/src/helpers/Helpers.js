import axios from 'axios';
import Vue from 'vue';
import VueFlashMessage from 'vue-flash-message';
import 'vue-flash-message/dist/vue-flash-message.min.css';

Vue.use(VueFlashMessage, {
    messageOptions: {
        timeout: 3000,
        pauseOnInteract: true
    }
});

const vm = new Vue();
const baseURL = 'http://localhost:8080/api/v1/';

const handleError = fn => (...params) =>
    fn(...params).catch(error => {
        vm.flash(`${error.response.status}: ${error.response.statusText}`, 'error');
    });

export const api = {

    // Tasks
    gettask: handleError(async id => {
        const res = await axios.get(baseURL + id);
        return res.data;
    }),
    gettasks: handleError(async () => {
        const res = await axios.get(baseURL);
        return res.data;
    }),
    getMaxCd: handleError(async () => {
        const res = await axios.get(baseURL + 'maxcd');
        return res.data;
    }),
    deletetask: handleError(async id => {
        const res = await axios.delete(baseURL + id);
        return res.data;
    }),
    createtask: handleError(async payload => {
        const res = await axios.post(baseURL, payload);
        return res.data;
    }),
    updatetask: handleError(async payload => {
        const res = await axios.put(baseURL + payload._id, payload);
        return res.data;
    }),

    // Estimate
    createEatimate: handleError(async payload => {
        const res = await axios.post(baseURL + 'estimates', payload);
        return res.data;
    }),
    updateEstimate: handleError(async payload => {
        const res = await axios.put(baseURL + 'estimates/' + payload.id, payload);
        return res.data;
    }),
    getEstimate: handleError(async id => {
        const res = await axios.get(baseURL + 'estimates/' + id);
        return res.data;
    }),
    deleteEatimate: handleError(async id => {
        const res = await axios.delete(baseURL + 'estimates/' + id);
        return res.data;
    }),

    // EstimateDetail
    getEstimateDetailsByEstimateId: handleError(async estimateId => {
        const res = await axios.get(baseURL + 'estimate-details/estimate-id/' + estimateId);
        return res.data;
    }),
    createEatimateDetail: handleError(async payload => {
        const res = await axios.post(baseURL + 'estimate-details', payload);
        return res.data;
    }),
    deleteEatimateDetail: handleError(async id => {
        const res = await axios.delete(baseURL + 'estimate-details/' + id);
        return res.data;
    }),
    deleteEstimateDetailsByEstimateId: handleError(async estimateId => {
        const res = await axios.delete(baseURL + 'estimate-details/estimate-id/' + estimateId);
        return res.data;
    }),

    // Customers
    getCustomersByCd: handleError(async cd => {
        const res = await axios.get(baseURL + 'customers/' + cd);
        return res.data;
    }),
    getCustomersByName: handleError(async name => {
        const res = await axios.get(baseURL + 'customers/name?name=' + name);
        return res.data;
    }),

    // Employees
    getEmployeesByCd: handleError(async cd => {
        const res = await axios.get(baseURL + 'employees/' + cd);
        return res.data;
    }),
    getEmployeesByName: handleError(async name => {
        const res = await axios.get(baseURL + 'employees/name?name=' + name);
        return res.data;
    }),

    // Products
    getProductByCd: handleError(async cd => {
        const res = await axios.get(baseURL + 'products/' + cd);
        return res.data;
    }),
    getProductsByName: handleError(async name => {
        const res = await axios.get(baseURL + 'products/name?name=' + name);
        return res.data;
    }),
};