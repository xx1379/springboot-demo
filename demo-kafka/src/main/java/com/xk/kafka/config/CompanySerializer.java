package com.xk.kafka.config;

import com.xk.kafka.po.Company;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/24 22:37
 */
public class CompanySerializer implements Serializer<Company> {
    @Override
    public byte[] serialize(String topic, Company company) {
        if (company == null) {
            return null;
        }
        byte[] name, address;
        try {
            if (company.getName() != null) {
                name = company.getName().getBytes("UTF-8");
            } else {
                name = new byte[0];
            }

            if (company.getAddress() != null) {
                address = company.getAddress().getBytes("UTF-8");
            } else {
                address = new byte[0];
            }

            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + name.length + address.length);
            buffer.putInt(name.length);
            buffer.put(name);
            buffer.putInt(address.length);
            buffer.put(address);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
