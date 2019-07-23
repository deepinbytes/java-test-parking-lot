require 'spec_helper'

RSpec.describe 'End To End Suite' do
  describe "full scenarios" do
    let(:commands) do
      [
          "create_parking_lot 6\n",
          "park KA-01-HH-1234 White\n",
          "park KA-01-HH-9999 White\n",
          "park KA-01-BB-0001 Black\n",
          "park KA-01-HH-7777 Red\n",
          "park KA-01-HH-2701 Blue\n",
          "park KA-01-HH-3141 Black\n",
          "leave 4\n",
          "park KA-01-P-333 White\n",
          "park DL-12-AA-9999 White\n",
          "registration_numbers_for_cars_with_colour White\n",
          "slot_numbers_for_cars_with_colour White\n",
          "slot_number_for_registration_number KA-01-HH-3141\n",
          "slot_number_for_registration_number MH-04-AY-1111\n"
      ]
    end

    let(:expected) do
      [
"Created parking lot with 6 slots
Allocated slot number: 1
Allocated slot number: 2
Allocated slot number: 3
Allocated slot number: 4
Allocated slot number: 5
Allocated slot number: 6
Slot number 4 is free
Slot No.	Registration No.	Color
1		KA-01-HH-1234		White
2		KA-01-HH-9999		White
3		KA-01-BB-0001		Black
5		KA-01-HH-2701		Blue
6		KA-01-HH-3141		Black
Allocated slot number: 4
Sorry, parking lot is full
KA-01-HH-1234,KA-01-HH-9999,KA-01-P-333
1,2,4
6
Not Found\n"
      ]

    end

        let(:expected_interactive) do
      [
          "Created parking lot with 6 slots\n",
          "Allocated slot number: 1\n",
          "Allocated slot number: 2\n",
          "Allocated slot number: 3\n",
          "Allocated slot number: 4\n",
          "Allocated slot number: 5\n",
          "Allocated slot number: 6\n",
          "Slot number 4 is free\n",
          "Allocated slot number: 4\n",
          "Sorry, parking lot is full\n",
          "KA-01-HH-1234,KA-01-HH-9999,KA-01-P-333\n",
          "1,2,4\n",
          "6\n",
          "Not Found\n"
      ]

    end


    it "input from file" do
      pty = PTY.spawn("parking_lot #{File.join(File.dirname(__FILE__), '..', 'fixtures', 'file_input.txt')}")
      print 'Testing file input: '
      str1 = fetch_stdout(pty)
      str2 = expected.join('').gsub(/\r/,'')
      expect(str1).to eq(str2)
    end

    it "interactive input" do
      pty = PTY.spawn("parking_lot")
      print 'Testing interactive input: '
      commands.each_with_index do |cmd, index|
        print cmd
        run_command(pty, cmd)
        expect(fetch_stdout(pty)).to end_with(expected_interactive[index])
      end
    end
  end
end
