#!/usr/bin/env python
# coding: utf-8

# In[ ]:


from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self):
        USER = 'aacuser'
        PASS = 'snhu2025'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 31658
        DB = 'AAC'
        COL = 'animals'
        #
        # Initialize Connection
        #
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER,PASS,HOST,PORT))
        self.database = self.client['%s' % (DB)]
        self.counter_collection = self.database['%s' % (COL)]
        
    def getNextRecordNum(self):
        result = self.counter_collection.find_one_and_update(
            {'_id': 'record_num'},
            {'$inc': {'seq':1}},
            return_document = True,
            upsert = True
        )
        return result['seq']

# Complete this create method to implement the C in CRUD.
    def create(self, data):
        if (data is not None) and (len(data) > 0): #Ensures that data is not none and is a list of dictionaries
            for i in data: #creates a loop in which the records will get the record number 
                index_num = self.getNextRecordNum()
                
                #Debug Statement
                #print("Testing in create")
                #print(index_num)
                #print(i)

                i.update({"rec_num":index_num}) #this will update the existing record number
                i.pop("_id", None)#this will remove the id if it exists and returns that value
                
                ret = self.database.animals.insert_one(i)
                if ret.inserted_id.is_valid(ret.inserted_id): #verifies that it is a valid ObjectID
                    continue #and then continues if valid
        else:
            raise Exception("Nothing to save, because the data parameter is empty")
            return False
        return True #returns true if the else/exception branch isn't taken
        
# Create method to implement the R in CRUD.
    def read(self, searchData):
            if searchData is not None:
                cursor = self.database.animals.find(searchData)
                out = []
                for doc in cursor:
                    out.append(doc)
                    
                    #Debug Statement
                    #numRec = len(out)
                    #print("Returning %d records..." % (numRec))
            else:
                out = self.database.animals.find_one()
            print(out)
            return out
            
            
    def con_test(self):
        out.self.database.animals.find_one()
        return out
        
# Create method to implement the U in CRUD.
    def update(self, searchData, updateData):
        if updateData is not None: #Verifies the information is not None
            result = self.database.animals.update_many(searchData, { "$set": updateData})
            print("Updated Data Entry:", result.raw_result)
            return result.modified_count
        else:
            raise Exception("Nothing to update as the update data is empty")
    
# Create method to implement the D in CRUD.
    def delete(self, deleteData):
        if deleteData is not None:
            result = self.database.animals.delete_many(deleteData)
            print("Deleted Data Entries:", result.raw_result)
        else:
            raise Exception("Nothing to delete as no parameters were given")
        