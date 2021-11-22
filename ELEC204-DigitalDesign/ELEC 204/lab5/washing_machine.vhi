
-- VHDL Instantiation Created from source file washing_machine.vhd -- 02:00:45 12/13/2019
--
-- Notes: 
-- 1) This instantiation template has been automatically generated using types
-- std_logic and std_logic_vector for the ports of the instantiated module
-- 2) To use this template to instantiate this entity, cut-and-paste and then edit

	COMPONENT washing_machine
	PORT(
		clk : IN std_logic;
		start : IN std_logic;
		reset : IN std_logic;          
		state : OUT std_logic_vector(3 downto 0);
		dec_out : OUT std_logic;
		zero_out : OUT std_logic;
		curr_state_binary : OUT std_logic_vector(1 downto 0)
		);
	END COMPONENT;

	Inst_washing_machine: washing_machine PORT MAP(
		clk => ,
		start => ,
		reset => ,
		state => ,
		dec_out => ,
		zero_out => ,
		curr_state_binary => 
	);


